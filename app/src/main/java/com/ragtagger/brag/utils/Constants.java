package com.ragtagger.brag.utils;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import com.ragtagger.brag.BuildConfig;

import android.content.Context;

import com.ragtagger.brag.R;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class Constants {
    //font type
    //public static final int FONT_SANS_ROUNDED = 1;
    public static final int FONT_OPENSANS_REGULAR = 1;
    public static final int FONT_OPENSANS_LIGHT = 2;
    public static final int FONT_OPENSANS_BOLD = 3;
    public static final int FONT_OPENSANS_SEMI_BOLD = 4;

    public static final int EXIT_LEFT = 1;
    public static final int EXIT_BOTTOM = 2;

    //bundle
    public static final String BUNDLE_IMAGE_LIST = "bundleimageList";
    public static final String BUNDLE_POSITION = "bundleposition";
    public static final String BUNDLE_QTY = "bundleqty";
    public static final String BUNDLE_PRODUCT_SORTING = "bundleProductSort";
    public static final String BUNDLE_SELETED_PRODUCT = "bundleselectedProduct";
    public static final String BUNDLE_PRODUCT_LISt = "bundleProductList";
    public static final String BUNDLE_IMAGE_URL = "imageurl";
    public static final String BUNDLE_SIZE_GUIDE = "bundleSizeGuide";
    public static final String BUNDLE_MOBILE = "bundlemobile";
    public static final String BUNDLE_EMAIL = "bundleemail";
    public static final String BUNDLE_PASSWORD = "bundlePassword";
    public static final String BUNDLE_PROFILE_IS_FROM = "bundleProfileIsFrom";
    public static final String BUNDLE_IS_FROM_SIGNUP = "bundleisFromsignup";
    public static final String BUNDLE_OTP = "bundleotp";
    public static final String BUNDLE_DEVICE_TOKEN = "devicetoken";
    public static final String BUNDLE_NOTIFICATION_MODEL = "notificationModel";
    public static final String BUNDLE_KEY_NOTIFICATION_ID = "notificationId";
    public static final String BUNDLE_KEY_NOTIFICATION_WHAT_ID = "notificationWhatId";
    public static final String BUNDLE_KEY_PRODUCT_LIST_TITLE = "bundleProductListTitle";
    public static final String LOCALBROADCAST_UPDATE_NOTIFICATION = "localbrodUpdateNotification";
    public static final String BUNDLE_TITLE = "bunbletitle";
    public static final String BUNDLE_SUBTITLE = "bunblesubtitle";
    public static final String BUNDLE_CART_LIST = "bundlecartlist";
    public static final String BUNDLE_ORDER_ID = "bundleorderId";
    public static final String BUNDLE_ORDER_DATA = "bundleorderData";
    public static final String BUNDLE_CATEGORY_LIST = "bundlecategorylist";
    public static final String BUNDLE_CATEGORY_NAME = "bundleCategoryId";
    public static final String BUNDLE_SUB_CATEGORY_NAME = "bundleSubCategoryId";
    public static final String BUNDLE_SEASON_CODE = "bundleSeasonCode";
    public static final String BUNDLE_APPLIED_FILTER = "bundleAppliedFilter";

    public static final int EXIT_TIME_INTERVAL = 2000;
    public static final String BUNDLE_KEY_STATE = "bundlestate";
    public static final String BUNDLE_KEY_STATE_LIST = "bundlestatelist";
    public static final String BUNDLE_NUM_STOCK = "bundlenumstock";
    public static final String BUNDLE_ADDRESS = "bundleaddress";
    public static final String LOCALBROADCAST_UPDATE_PROFILE = "localbroadcastupdateprofile";
    public static final String BUNDLE_IS_ADDRESS_UPDATE = "bundleupdateaddress";

    public static final String ACTION_UPDATE_CART_ICON_STATE = "action_update_cart_icon_state";

    public static final String OTP_FORMAT = "RTBRAG";
    public static final String BUNDLE_KEY_MOBILE_NUM = "bundlemobile";

    public interface ApiHelper {
        String BASE_URL = "http://27.54.166.146:8090/BRAGWeb/api/";//demo
//        String BASE_URL = "http://192.168.131.10:8091/BRAGWeb/api/"; //local
        //String BASE_URL = "http://192.168.131.125:8085/BRAGWeb/api/"; //tushar

        String API_VERSION = "v1/";
        String APP_VERSION = BuildConfig.VERSION_NAME;
        String FULL_URL = BASE_URL + API_VERSION;

        String OS = "Android";

        String MAP_KEY_ACCESS_TOKEN = "access-token";
        String MAP_KEY_DEVICE_TOKEN = "device-token";
        String MAP_KEY_DEVICE_TYPE = "device-type";
        String MAP_APP_VERSION = "app-version";
        String MAP_KEY_OSV = "os-version";
        String MAP_KEY_OS = "os";
        String MAP_KEY_CART_NUM = "itemnoofcart";
        String MAP_KEY_NOTIFICATION_NUM = "unreadcount";
    }

    public interface IErrorCode {
        int defaultErrorCode = 5001;
        int notInternetConnErrorCode = 5002;
        int ioExceptionCancelApiErrorCode = 5003;
        int ioExceptionOtherErrorCode = 5004;
        int socketTimeOutError = 5006;
    }

    public interface IErrorMessage {
        String CONTENT_NOT_MODIFIED = "Content not modified";
        String OTHER_EXCEPTION = "We could not complete your request";
        String SOMETHING_WRONG_ERROR = "Something went wrong!!\nPlease try again later.";

        String IO_EXCEPTION = "Something went wrong!!\nPlease try again later.";
        String NO_INTERNET_CONNECTION = "No internet connection.";
        String IO_EXCEPTION_CANCEL_API = "Something went wrong!!\nPlease try again later.";
        String IO_EXCEPTION_OTHER_ERROR = "Something went wrong!!\nPlease try again later.";
        String TIME_OUT_CONNECTION = "Connection timeout.\nPlease try again later.";
    }

    public interface IPermissionRequestCode {
        int REQ_SMS_SEND_RECEIVED_READ = 1001;
    }


    public enum MoreList {
        MY_ORDER(1),
        PRIVACY_POLICY(2),
        TERMS_AND(3),
        CHANGE_PASS(4),
        LOGOUT(5),
        CHANGE_MOBILE(6),
        NOTIFICATION(7),
        USER_PROFILE(8),
        CHANGE_ADDRESS(9),
        FAQ(10),
        ABOUT_US(11);

        MoreList(int i) {
            this.type = i;
        }

        private int type;

        public int getNumericType() {
            return type;
        }
    }

    /*public enum NotificationType {
        TEXT,
        OTHER,
        LOGIN;
    }*/

    public enum NotificationType {
        TEXT,
        USER,
        ORDER,
        ITEM;
    }

    public enum OTPValidationIsFrom {
        SIGN_UP,
        FORGET_PASS,
        CHANGE_MOBILE;
    }

    public enum ProfileIsFrom {
        CHANGE_PASS,
        CHANGE_MOBILE,
        UPDATE_PROFILE,
        ADD_EDIT_ADDRESS

    }

    public enum VerificationStatus {
        AWAITING_OTP_VALIDATION,
        AWAITING_VERIFICATION,
        APPROVED,
        REJECTED,
        AWAITING_FOR_PASSWORD_RESET;
    }


    public enum OrderStatus {
        PLACED(0),
        APPROVED(1),
        REJECTED(2),
        CANCELED(3),
        DISPATCHED(4),
        DELIVERED(5);

        OrderStatus(int i) {
            this.type = i;
        }

        private int type;

        public int getNumericType() {
            return type;
        }

        public static OrderStatus fromId(int number) {
            try {
                for (OrderStatus e : OrderStatus.values()) {
                    if (e.getNumericType() == number) {
                        return e;
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static String getOrderStatusLabel(Context context, int i) {
            switch (OrderStatus.values()[i]) {
                case PLACED:
                    return context.getString(R.string.label_order_status_placed);
                case APPROVED:
                    return context.getString(R.string.label_order_status_approved);
                case REJECTED:
                    return context.getString(R.string.label_order_status_rejected);
                case CANCELED:
                    return context.getString(R.string.label_order_status_cancalled);
                case DISPATCHED:
                    return context.getString(R.string.label_order_status_dispatched);
                case DELIVERED:
                    return context.getString(R.string.label_order_status_delivered);
                default:
                    return "";
            }
        }
    }

    public enum SortBy {
        PRICE_ASC,
        PRICE_DESC,
        DATE_ASC,
        DATE_DESC;
    }


}

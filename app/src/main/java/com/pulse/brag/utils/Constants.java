package com.pulse.brag.utils;

/**
 * Copyright (c) 2015-2016 Sailfin Technologies, Pvt. Ltd.  All Rights Reserved.
 * This software is the confidential and proprietary information
 * (Confidential Information) of Sailfin Technologies, Pvt. Ltd.  You shall not
 * disclose or use Confidential Information without the express written
 * agreement of Sailfin Technologies, Pvt. Ltd.
 */

import android.content.Context;

import com.pulse.brag.R;

/**
 * Created by nikhil.vadoliya on 25-09-2017.
 */


public class Constants {

    // TODO: Release : Log and Base Url
    public static final boolean IS_LOG_ENABLED = true;

    //font type
//    public static final int FONT_SANS_ROUNDED = 1;
    public static final int FONT_OPENSANS_REGULAR = 1;
    public static final int FONT_OPENSANS_LIGHT = 2;
    public static final int FONT_OPENSANS_BOLD = 3;

    public static final int EXIT_LEFT = 1;
    public static final int EXIT_BOTTOM = 2;

    //bundle
    public static final String BUNDLE_IMAGE_LIST = "bundleimageList";
    public static final String BUNDLE_POSITION = "bundleposition";
    public static final String BUNDLE_QTY = "bundleqty";
    public static final String BUNDLE_PRODUCT_SORTING = "bundleProductSort";
    public static final String BUNDLE_SELETED_PRODUCT = "bundleselectedProduct";
    public static final String BUNDLE_IMAGE_URL = "imageurl";
    public static final String BUNDLE_MOBILE = "bundlemobile";
    public static final String BUNDLE_EMAIL = "bundleemail";
    public static final String BUNDLE_PROFILE_IS_FROM = "bundleProfileIsFrom";
    public static final String BUNDLE_IS_FROM_SIGNUP = "bundleisFromsignup";
    public static final String BUNDLE_IS_FROM_CHANGE_PASS = "bundleisfromchangepass";
    public static final String BUNDLE_OTP = "bundleotp";
    public static final String BUNDLE_PRODUCT_NAME = "bundleproductname";
    public static final String BUNDLE_PRODUCT_IMG = "bundleproductImg";
    public static final String BUNDLE_DEVICE_TOKEN = "devicetoken";
    public static final String BUNDLE_NOTIFICATION_MODEL = "notificationModel";
    public static final String BUNDLE_KEY_NOTIFICATION_ID = "notificationId";


    //header
    public static final String HEADER_KEY_VERSION_NAME = "versionName";
    public static final String HEADER_KEY_ACCESS_TOKEN = "accessToken";
    public static final String HEADER_KEY_DEVICE_TOKEN = "devicesToken";
    public static final String HEADER_KEY_DEVICE_TYPE = "deviceType";
    public static final String HEADER_KEY_OSV = "osVersion";


    public static final String HEADER_KEY_OS = "Os";
    public static final int EXIT_TIME_INTERVAL = 2000;
    public static final int SUCCESS_RESPONE = 200;


    public static final String LOCALBROADCAST_UPDATE_NOTIFICATION = "localbrodUpdateNotification";
    public static final String BUNDLE_TITLE = "bunbletitle";
    public static final String BUNDLE_SUBTITLE = "bunblesubtitle";
    public static final String BUNDLE_CART_LIST = "bundlecartlist";
    public static final String BUNDLE_ORDER_ID = "bundleorderId";
    public static final String BUNDLE_ORDER_DATA = "bundleorderData";

    public interface ApiHelper {
        //        String BASE_URL = "http://103.204.192.148/brag/api/";
        String BASE_URL = "http://103.204.192.148/BRAGWeb/api/";
        String API_VERSION = "v1/";
        String FULL_URL = BASE_URL + API_VERSION;

        String OS = "Android";

        String MAP_KEY_ACCESS_TOKEN = "access-token";
        String MAP_KEY_DEVICE_TOKEN = "device-token";
        String MAP_KEY_DEVICE_TYPE = "device-type";
        String MAP_API_VERSION = "api-version";
        String MAP_KEY_OSV = "os-version";
        String MAP_KEY_OS = "os";
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
        String IO_EXCEPTION = "We could not complete your request";
        String NO_INTERNET_CONNECTION = "No internet connection.";
        String OTHER_EXCEPTION = "We could not complete your request";
        String SOMETHING_WRONG_ERROR = "Something went wrong!!\nPlease try again later.";
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
        USER_PROFILE(8);


        MoreList(int i) {
            this.type = i;
        }

        private int type;

        public int getNumericType() {
            return type;
        }
    }

    public enum NotificationType {
        TEXT,
        OTHER,
        LOGIN;
    }

    public enum OTPValidationIsFrom {
        SIGN_UP,
        FORGET_PASS,
        CHANGE_MOBILE;
    }

    public enum ProfileIsFrom {
        CHANGE_PASS,
        CHANGE_MOBILE,
        UPDATE_PROFILE

    }

    public enum VerificationStatus {
        AWAITING_OTP_VALIDATION,
        AWAITING_VERIFICATION,
        APPROVED,
        REJECTED,
        AWAITING_FOR_PASSWORD_RESET;
    }

    public enum ProductSorting {
        POPULARITY,
        PRICE_LOW_TO_HEIGH,
        PRICE_HEIGH_TO_LOW
    }

    public enum OrderStatus {
        ORDERED(1),
        APPROVED(2),
        PACKED(3),
        SHIPPED(4),
        DELIVERED(5),
        CANCALLED(6);

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
                case ORDERED:
                    return context.getString(R.string.label_order_status_ordered);
                case APPROVED:
                    return context.getString(R.string.label_order_status_approved);
                case SHIPPED:
                    return context.getString(R.string.label_order_status_shipped);
                case PACKED:
                    return context.getString(R.string.label_order_status_packed);
                case DELIVERED:
                    return context.getString(R.string.label_order_status_delivered);
                case CANCALLED:
                    return context.getString(R.string.label_order_status_cancalled);
                default:
                    return "";
            }
        }
    }


}

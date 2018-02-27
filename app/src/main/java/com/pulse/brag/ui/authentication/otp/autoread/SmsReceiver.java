package com.pulse.brag.ui.authentication.otp.autoread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by alpesh.rathod on 2/16/2018.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str = "";
        String pinNo = "";

        if (bundle != null) {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i = 0; i < smsm.length; i++) {
                smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                sms_str = smsm[i].getMessageBody().toString();
                pinNo = sms_str.replaceAll("[^0-9]", "");
            }
            if (mListener != null)
                mListener.messageReceived(pinNo);
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}

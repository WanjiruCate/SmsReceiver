package com.example.smsreceiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = " SmsBroadcastReceiver";
    String msg, phoneNo = "";
    public static final String pdu_type = "pdus";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        //Retrieve the general action to be perfomed and display on log

        Log.i(TAG, "Intent Received" + intent.getAction());
        if (intent.getAction().equals(SMS_RECEIVED)) {
            //Retrieve a map of extended data from the intent
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages;
            if (bundle != null) {

                //Create PDU (Protocol data unit) object which is a protocol for transferring message
                Object[] pduObject = (Object[]) bundle.get(pdu_type);
                if (pduObject != null) {
                     messages = new SmsMessage[pduObject.length];

                    for (int i = 0; i < pduObject.length; i++) {
                        //For build versions:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String format = bundle.getString("format");
                            //From PDU get all object and Smsmessage using the following code
                            messages[i] = SmsMessage.createFromPdu((byte[]) pduObject[i], format);

                        } else {
                            //API LEVEL 23
                            Log.d(TAG, "onReceive: API Level 23");
                            messages[i] = SmsMessage.createFromPdu((byte[]) pduObject[i]);
                        }
                        msg = messages[i].getMessageBody();
                        phoneNo = messages[i].getOriginatingAddress();
                    }
                    Toast.makeText(context, "Message: " + msg + "\nNumber" + phoneNo, Toast.LENGTH_LONG).show();
                    Log.d(TAG,msg + " " +phoneNo);
                } else {
                    Log.d(TAG, "onReceive: Null object pdu");
                }
            }
        }


    }
}

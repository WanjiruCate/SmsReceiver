package com.example.smsreceiver;

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
    String msg,phoneNo = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Retrieve the general action to be perfomed and display on log

        Log.i(TAG, "Intent Received" + intent.getAction());
        if (intent.getAction() == SMS_RECEIVED)
        {
            //Retrieve a map of extended data from the intent
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                //Create FDU (Protocol data unit) object which is a protocol for transferring message
                Object[] mypda = (Object[])bundle.get("mypda");
                final SmsMessage[] message = new SmsMessage[mypda.length];

                for (int i = 0;i <mypda.length;i++){
                    //For build versions:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = bundle.getString("format");
                        //From PDU get all object and Smsmessage using the following code
                        message[i] = SmsMessage.createFromPdu((byte[])mypda[i],format);

                    }
                    else{
                        //API LEVEL 23
                        message[i] = SmsMessage.createFromPdu((byte[])mypda[i]);
                    }
                    msg = message[i].getMessageBody();
                    phoneNo = message[i].getOriginatingAddress();
                }
                Toast.makeText(context,"Message: " + msg + "\nNumber" + phoneNo,Toast.LENGTH_LONG).show();
            }
        }


    }
}

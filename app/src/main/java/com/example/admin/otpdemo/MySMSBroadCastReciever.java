package com.example.admin.otpdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

/**
 * Created by Admin on 12/26/2017.
 */

public class MySMSBroadCastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        SmsMessage[] smsm=null;
        String sms_str="";
        if(bundle!=null){
            Object[] pdus = (Object[])bundle.get("pdus");
            smsm= new SmsMessage[pdus.length];
            for(int i=0;i<smsm.length;i++){
                smsm[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                sms_str+="\r\nMessage :";
                sms_str+=smsm[i].getMessageBody().toString();
                sms_str+="\r\n";
                String sender =smsm[i].getOriginatingAddress();
                if(sender=="9650650883" || sender=="8076074270" || sender=="8447383515");
                {
                    Intent smsIntent= new Intent("Otp");
                    smsIntent.putExtra("message",sms_str);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
                }

            }
        }
    }
}

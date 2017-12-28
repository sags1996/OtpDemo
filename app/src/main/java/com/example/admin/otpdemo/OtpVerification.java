package com.example.admin.otpdemo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OtpVerification extends AppCompatActivity  {
public static final int REQ_ID_MUL_PER=1;
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=(TextView)findViewById(R.id.TimerText);
        if(checkAndReqPermission()){

        }
        new CountDownTimer(30000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
               timer.setText("Resend the code in "+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                timer.setText("Please Re-enter your Mobile No.");

            }
        }.start();

    }
    private BroadcastReceiver receiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase("Otp")){
                final String message = intent.getStringExtra("message");
                TextView tv=(TextView)findViewById(R.id.otp);
                tv.setText(message);
                Button cnfirm=(Button)findViewById(R.id.confirm);
                cnfirm.setClickable(true);
                cnfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //otp input is right or wrong
                        // DB check
                        //session implement
                        //new activity should open


                        Intent intent1=getIntent();
                        String mobile_num=intent1.getStringExtra("mob");
                        SharedPreferences sharedPreferences=getSharedPreferences("mypref",OtpVerification.this.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mobilenumber",mobile_num);
                        editor.commit();

                        Intent i=new Intent(OtpVerification.this,MainPage.class);
                        startActivity(i);


                    }
                });

            }
        }
    };
    private  boolean checkAndReqPermission(){
        int permissionSendMessage= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int recieveSMS=ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS);
        int readSMS=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded= new ArrayList<>();
        if(recieveSMS!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if(readSMS!=PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);

        }
        if(permissionSendMessage!= PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if(!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQ_ID_MUL_PER);
        return false;
        }
        return true;
    }
    @Override
    public void onResume(){
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,new IntentFilter("Otp"));
        super.onResume();

    }
    @Override
    public void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}

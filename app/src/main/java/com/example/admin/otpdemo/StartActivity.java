package com.example.admin.otpdemo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent i=new Intent(StartActivity.this,MainPage.class);
                StartActivity.this.startActivity(i);
                StartActivity.this.finish();
            }
        },3000);


    }
}

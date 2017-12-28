package com.example.admin.otpdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        SharedPreferences sharedPreferences=getSharedPreferences("mypref",this.MODE_PRIVATE);
        if(sharedPreferences.contains("mobilenumber")){
            Toast.makeText(this,"Welcome "+sharedPreferences.getString("mobilenumber",""),Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Main2Activity.this,MainPage.class);
            startActivity(intent);
        }

        final EditText editText1=(EditText)findViewById(R.id.mobile);
        final EditText editText2=(EditText)findViewById(R.id.staff);
        Button b = (Button) findViewById(R.id.login);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText1.getText().length()==0 && editText2.getText().length()==0)
                    Toast.makeText(Main2Activity.this,"Enter Mobile No and Staff No first",Toast.LENGTH_SHORT).show();
                else if(editText1.getText().length()==0 && editText2.getText().length()!=0)
                    Toast.makeText(Main2Activity.this,"Enter Mobile No ",Toast.LENGTH_SHORT).show();
                else if(editText1.getText().length()!=0 && editText2.getText().length()==0)
                    Toast.makeText(Main2Activity.this,"Enter Staff No ",Toast.LENGTH_SHORT).show();
                else {
                    String mobile_num = editText1.getText().toString();
                    String staff_num = editText2.getText().toString();
                    Intent i = new Intent(Main2Activity.this, OtpVerification.class);
                    i.putExtra("mob",mobile_num);
                    startActivity(i);

                }
            }
        });

    }
}

package com.tni.phitchaya.sdk_authentication_th_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CAMERA = 102;
    FloatingActionButton Camera_Bt;
    Context context;
//    public static final String BASEURL = "http://34.142.220.112/";
//    public static final String BASEURL = "https://hewsdev.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getBaseContext();

        Camera_Bt = findViewById(R.id.Open_Camera);
        Camera_Bt.setOnClickListener(this);
        getSupportActionBar().hide();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.Open_Camera:

                Intent intent = new Intent(MainActivity.this, Camera_IDcard_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                break;
            default:

                break;
        }


    }
}
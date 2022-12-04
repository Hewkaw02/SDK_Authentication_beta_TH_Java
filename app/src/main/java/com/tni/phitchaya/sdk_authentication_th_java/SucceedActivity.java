package com.tni.phitchaya.sdk_authentication_th_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SucceedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succeed);
        getSupportActionBar().hide();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
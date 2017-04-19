package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.parse.ParseUser;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {

    public static final int DELAY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CountDownTimer timer = new CountDownTimer(DELAY * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }
}

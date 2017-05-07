package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.bumptech.glide.Glide;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    public static final int DELAY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Glide.with(this)
                .load(R.drawable.splash_animation)
                .asGif()
                .into((ImageView)findViewById(R.id.gif));

        new CountDownTimer(DELAY * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                finish();
                openMainView();
            }
        }.start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void openMainView() {
        Intent intent = new Intent(SplashActivity.this, DrawerActivity.class);
        startActivity(intent);
        finish();
    }
}

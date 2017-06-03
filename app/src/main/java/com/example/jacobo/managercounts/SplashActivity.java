package com.example.jacobo.managercounts;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private  static final long SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Establece el modo
        //ActionBar a = getActionBar();//ocultar actionBar
        //a.hide();
//        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i =new Intent().setClass(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        };
        Timer timer =new Timer();
        timer.schedule(task,SPLASH_DELAY);
    }
}

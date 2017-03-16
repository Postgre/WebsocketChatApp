package com.calenaur.chat.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.calenaur.chat.R;
import com.calenaur.chat.listener.FadeOutAnimationListener;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Animation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(1000);
        RelativeLayout contentHolder = (RelativeLayout) findViewById(R.id.contentHolder);
        contentHolder.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new FadeOutAnimationListener(this, Auth.class));
    }
}

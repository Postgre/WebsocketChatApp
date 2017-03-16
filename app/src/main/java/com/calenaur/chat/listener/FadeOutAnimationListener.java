package com.calenaur.chat.listener;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;

public class FadeOutAnimationListener implements Animation.AnimationListener {

    private AppCompatActivity currentActivity;
    private Class newActivity;

    public FadeOutAnimationListener(AppCompatActivity currentActivity, Class newActivity){
        this.currentActivity = currentActivity;
        this.newActivity = newActivity;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(currentActivity, newActivity);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

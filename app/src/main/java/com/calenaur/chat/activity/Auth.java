package com.calenaur.chat.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.calenaur.chat.R;
import com.calenaur.chat.websocket.Client;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;

public class Auth extends AppCompatActivity implements WebsocketActivity {

    private TextView txtState;
    private LinearLayout contentHolder;
    private LinearLayout spinnerHolder;
    private Animation fadeOut;
    private Client client;

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        contentHolder = (LinearLayout) findViewById(R.id.contentHolder);
        spinnerHolder = (LinearLayout) findViewById(R.id.spinnerHolder);
        fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setFillAfter(true);
        fadeOut.setDuration(2500);
        txtState = (TextView) findViewById(R.id.txtState);
        contentHolder.setVisibility(View.INVISIBLE);
        try {
            client = new Client(new URI("ws://calenaur.com:9998"), this);
            client.connect();
        } catch (URISyntaxException e) {
            setState(Client.DISCONNECTED);
        }
    }

    @Override
    public void setState(int state){
        switch (state){
            case Client.CONNECTED:
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contentHolder.setVisibility(View.VISIBLE);
                                spinnerHolder.setVisibility(View.INVISIBLE);

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtState.setText("Connected");
                        spinnerHolder.startAnimation(fadeOut);

                    }
                });
                break;
            case Client.DISCONNECTED:
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtState.setText("Could not connect");
                        spinnerHolder.startAnimation(fadeOut);
                    }
                });
                break;
        }
    }

}

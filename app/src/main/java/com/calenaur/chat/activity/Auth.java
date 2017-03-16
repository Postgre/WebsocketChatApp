package com.calenaur.chat.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.calenaur.chat.R;
import com.calenaur.chat.websocket.Client;

import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;

public class Auth extends AppCompatActivity implements WebsocketActivity {

    private TextView txtState;
    private LinearLayout authHolder;
    private LinearLayout spinnerHolder;
    private RelativeLayout chatHolder;
    private EditText etxtUsername;
    private EditText etxtPassword;
    private EditText etxtInput;
    private LinearLayout logHolder;
    private ProgressBar spinner;
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
        authHolder = (LinearLayout) findViewById(R.id.authHolder);
        spinnerHolder = (LinearLayout) findViewById(R.id.spinnerHolder);
        logHolder = (LinearLayout) findViewById(R.id.logHolder);
        chatHolder = (RelativeLayout) findViewById(R.id.chatHolder);
        etxtUsername = (EditText) findViewById(R.id.etxtUsername);
        etxtPassword = (EditText) findViewById(R.id.etxtPassword);
        etxtInput = (EditText) findViewById(R.id.etxtInput);
        spinner = (ProgressBar) findViewById(R.id.spinner);
        fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setFillAfter(true);
        fadeOut.setDuration(2500);
        txtState = (TextView) findViewById(R.id.txtState);
        authHolder.setVisibility(View.INVISIBLE);
        chatHolder.setVisibility(View.INVISIBLE);
        try {
            client = new Client(new URI("ws://calenaur.com:9998"), this);
            client.connect();
        } catch (URISyntaxException e) {
            setState(Client.DISCONNECTED);
        }
    }

    public void authorize(View view){
        client.authorize(etxtUsername.getText().toString(), etxtPassword.getText().toString());
    }

    public void send(View view){
        client.sendMessage(etxtInput.getText().toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                etxtInput.setText("");
            }
        });
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
                                authHolder.setVisibility(View.VISIBLE);
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
                        spinner.setVisibility(View.INVISIBLE);
                        chatHolder.setVisibility(View.INVISIBLE);
                        authHolder.setVisibility(View.INVISIBLE);
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
                        System.exit(0);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtState.setText("Disconnected");
                        chatHolder.setVisibility(View.INVISIBLE);
                        authHolder.setVisibility(View.INVISIBLE);
                        spinner.setVisibility(View.INVISIBLE);
                        spinnerHolder.setVisibility(View.VISIBLE);
                        spinnerHolder.startAnimation(fadeOut);
                    }
                });
                break;
            case Client.AUTHORIZED:
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatHolder.setVisibility(View.VISIBLE);
                                authHolder.setVisibility(View.INVISIBLE);
                                logHolder.setVisibility(View.VISIBLE);
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
                        txtState.setText("Authorized");
                        authHolder.setVisibility(View.INVISIBLE);
                        spinnerHolder.setVisibility(View.VISIBLE);
                        spinnerHolder.startAnimation(fadeOut);
                    }
                });
                break;
        }
    }

    @Override
    public void addToLog(String s) {
        runOnUiThread(new Runnable() {
            AppCompatActivity activity;
            String msg;
            @Override
            public void run() {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView textView = new TextView(activity);
                textView.setText(msg);
                textView.setLayoutParams(layoutParams);
                textView.setVisibility(View.VISIBLE);
                logHolder.addView(textView);
            }

            public Runnable init(AppCompatActivity activity, String msg){
                this.activity = activity;
                this.msg = msg;
                return(this);
            }
        }.init(this, s));
    }

}

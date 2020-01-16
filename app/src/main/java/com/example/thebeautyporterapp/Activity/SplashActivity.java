package com.example.thebeautyporterapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    int logedIn = 0;
    String countryID = "0";

    SharedPreferences preferences;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);
            Hawk.init(this)
                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                    .setStorage(HawkBuilder.newSqliteStorage(this))
                    .setLogLevel(LogLevel.FULL)
                    .build();
            logedIn = Hawk.get("logedIn", 0);

            /* New Handler to start the Menu-Activity
             * and close this Splash-Screen after some seconds.*/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {

                        if (logedIn == 0) {
                            Intent i = new Intent(SplashActivity.this, LogInMethodActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }

//                        // Start your app main activity
                    } catch (Exception ex) {
                        String msg = ex.getMessage().toString();
                        String j = msg;
                    }
//
                }
            }, SPLASH_DISPLAY_LENGTH);
        } catch (Exception ex) {
            String ms = ex.getMessage().toString();
            String y = ms;
        }
    }
}
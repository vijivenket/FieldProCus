package com.capricot.fieldprocustomer.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.capricot.fieldprocustomer.Home;
import com.capricot.fieldprocustomer.Login;
import com.capricot.fieldprocustomer.R;

public class SplashScreen extends Activity {

    // Data Store
    private Boolean isFirstTime;
    private SharedPreferences.Editor editor;
    private SharedPreferences app_preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);



        app_preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);

        editor = app_preferences.edit();
        editor.apply();

        // Splash screen timer
        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirstTime = app_preferences.getBoolean("isFirstTime", true);

                if (isFirstTime) {
                    Intent i = new Intent(SplashScreen.this, Login.class);
                    startActivity(i);
                   /*  editor.putBoolean("isFirstTime",false);
                    editor.apply();*/
                  //  finish();

                }
                else {
                    Intent i = new Intent(SplashScreen.this, Home.class);
                    startActivity(i);
                    finish();
                }

                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
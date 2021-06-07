package com.movtech.smartgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    boolean sudahLogin;
    String role;

    private int splashtime = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        sudahLogin = sharedPreferences.getBoolean("sudahLogin", false);
        role = sharedPreferences.getString("user", "0");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if (sudahLogin && role.equals("viewer")) {
                    i = new Intent(SplashScreen.this, BerandaUser.class);
                } else if (sudahLogin && role.equals("admin")){
                    i = new Intent(SplashScreen.this, AdminActivity.class);
                }
                else {
                    i = new Intent(SplashScreen.this, LoginRole.class);
                }
                startActivity(i);
                finish();
            }
        }, splashtime);
    }
}
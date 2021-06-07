package com.movtech.smartgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView logout = findViewById(R.id.tv_logout);
        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        role = sharedPreferences.getString("role", "");

        logout.setText("Logout "+role);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (role){
                    case "viewer":
                        logout();
                        break;
                    case "admin":
                        logout();
                        break;
                    case "":
                        FirebaseAuth.getInstance().signOut();
                        logout();
                        break;
                }
            }
        });
    }
    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", null);
        editor.putString("role", null);
        editor.putBoolean("sudahLogin", false);
        editor.apply();
        finish();
        startActivity(new Intent(MainActivity.this, LoginRole.class));
    }
}
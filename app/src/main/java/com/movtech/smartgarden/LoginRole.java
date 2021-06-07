package com.movtech.smartgarden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.movtech.smartgarden.ui.login.LoginActivity;

public class LoginRole extends AppCompatActivity implements View.OnClickListener {
    CardView btnAdmin, btnUser;
    static LoginRole roleActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_role);

        roleActivity=this;
        initComponent();
    }
    public static LoginRole getInstance(){
        return roleActivity;
    }

    public void initComponent(){
        btnAdmin = findViewById(R.id.btn_admin);
        btnUser = findViewById(R.id.btn_user);

        btnAdmin.setOnClickListener(this);
        btnUser.setOnClickListener(this);

    }

    public void ClickOpen(int i){
        Intent intent = new Intent(LoginRole.this, LoginActivity.class);
        intent.putExtra("kode",i);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_admin :
                ClickOpen(1);
                break;
            case R.id.btn_user :
                ClickOpen(2);
                break;
        }
    }
}